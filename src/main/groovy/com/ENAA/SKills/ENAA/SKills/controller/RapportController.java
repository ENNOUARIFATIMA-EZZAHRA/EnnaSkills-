package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.dto.ProgressionDto;
import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.service.ApprenantService;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import com.ENAA.SKills.ENAA.SKills.service.ValidationSousCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api/rapport")
public class RapportController {
    @Autowired
    private ApprenantService apprenantService;
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private ValidationSousCompetenceService validationService;

    @GetMapping("/progression/{apprenantId}")
    public ProgressionDto getProgression(@PathVariable Long apprenantId) {
        Optional<Apprenant> apprenantOpt = apprenantService.findById(apprenantId);
        if (apprenantOpt.isEmpty()) return null;
        Apprenant apprenant = apprenantOpt.get();
        ProgressionDto dto = new ProgressionDto();
        dto.apprenantId = apprenant.getId();
        dto.apprenantNom = apprenant.getNom();
        List<Competence> competences = competenceService.findAll();
        List<ValidationSousCompetence> validations = validationService.getAll();
        dto.competences = new ArrayList<>();
        for (Competence c : competences) {
            ProgressionDto.CompetenceProgression cp = new ProgressionDto.CompetenceProgression();
            cp.id = c.getId();
            cp.nom = c.getNom();
            cp.sousCompetences = new ArrayList<>();
            boolean allValid = true;
            if (c.getSousCompetences() != null) {
                for (SousCompetence sc : c.getSousCompetences()) {
                    ProgressionDto.SousCompetenceProgression scp = new ProgressionDto.SousCompetenceProgression();
                    scp.id = sc.getId();
                    scp.titre = sc.getTitre();
                    ValidationSousCompetence v = validations.stream()
                        .filter(val -> val.getApprenant().getId().equals(apprenantId) && val.getSousCompetence().getId().equals(sc.getId()))
                        .findFirst().orElse(null);
                    if (v != null) {
                        scp.statut = v.getStatut().name();
                        if (v.getStatut() != StatutValidation.VALIDE) allValid = false;
                    } else {
                        scp.statut = StatutValidation.NON_VALIDE.name();
                        allValid = false;
                    }
                    cp.sousCompetences.add(scp);
                }
            }
            cp.acquise = allValid && cp.sousCompetences.size() > 0;
            dto.competences.add(cp);
        }
        return dto;
    }

    @GetMapping("/export/csv/{apprenantId}")
    public void exportProgressionToCSV(@PathVariable Long apprenantId, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=progression.csv");

      
        ProgressionDto progression = getProgression(apprenantId);
        if (progression == null) {
            response.sendError(404, "Apprenant non trouvé");
            return;
        }

        PrintWriter writer = response.getWriter();
        // رأس الجدول
        writer.println("Apprenant,Competence,Sous-Competence,Statut");

        // اكتب كل سطر
        for (ProgressionDto.CompetenceProgression comp : progression.competences) {
            for (ProgressionDto.SousCompetenceProgression sous : comp.sousCompetences) {
                writer.println(progression.apprenantNom + "," + comp.nom + "," + sous.titre + "," + sous.statut);
            }
        }
        writer.flush();
        writer.close();
    }



    @GetMapping("/export/pdf/{apprenantId}")
    public void exportProgressionToPDF(@PathVariable Long apprenantId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=progression.pdf");

        ProgressionDto progression = getProgression(apprenantId);
        if (progression == null) {
            response.sendError(404, "Apprenant non trouvé");
            return;
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Rapport de progression de l'apprenant: " + progression.apprenantNom));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Apprenant");
            table.addCell("Competence");
            table.addCell("Sous-Competence");
            table.addCell("Statut");

            for (ProgressionDto.CompetenceProgression comp : progression.competences) {
                for (ProgressionDto.SousCompetenceProgression sous : comp.sousCompetences) {
                    table.addCell(progression.apprenantNom);
                    table.addCell(comp.nom);
                    table.addCell(sous.titre);
                    table.addCell(sous.statut);
                }
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            response.sendError(500, "Erreur lors de la génération du PDF: " + e.getMessage());
        }
    }
}
