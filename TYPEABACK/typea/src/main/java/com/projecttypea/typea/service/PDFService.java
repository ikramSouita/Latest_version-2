package com.projecttypea.typea.service;

import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.bean.Manifestation;
import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.bean.User;
import com.projecttypea.typea.security.enums.DemandeType;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class PDFService {
    private static final String PDF_RESOURCES = "/pdf-resources/";
    private SpringTemplateEngine springTemplateEngine;
    private MissionStageService missionStageService;
    private ManifestationService manifestationService;
    private DoneesProService doneesProService;


    public PDFService(SpringTemplateEngine springTemplateEngine, MissionStageService missionStageService, ManifestationService manifestationService, DoneesProService doneesProService) {
        this.springTemplateEngine = springTemplateEngine;
        this.missionStageService = missionStageService;
        this.manifestationService = manifestationService;
        this.doneesProService = doneesProService;
    }

    public File generateLettre(Long id, DemandeType type, String etat) throws Exception{
        Context context = null;

        if (type == DemandeType.MANIFESTATION){
            context = getContextManifestationPDF(id);
        }
        if (type == DemandeType.MISSION_STAGE){
            context = getContextMissionStagePDF(id);
        }

        String template = etat.equals("acceptation") ? "lettre_acceptation" : "lettre_refus";

        String html = loadAndFillTemplate(template, context);
        String xhtml = convertToXhtml(html);

        return renderPDF(xhtml);
    }

    public File generatePdfForDemande(Long id, DemandeType type) throws Exception{
        Context context = null;

        if (type == DemandeType.MANIFESTATION){
            context = getContextManifestationPDF(id);
        }
        if (type == DemandeType.MISSION_STAGE){
            context = getContextMissionStagePDF(id);
        }

        String template = "demande_details";

        String html = loadAndFillTemplate(template, context);
        String xhtml = convertToXhtml(html);

        return renderPDF(xhtml);
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException{
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);

        return out.toString();
    }

    private File renderPDF(String html) throws Exception{
        File file = File.createTempFile("lettre", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();

        return file;
    }

    private Context getContextMissionStagePDF(Long id){
        MissionStage missionStage = missionStageService.getById(id);
        Context context = new Context();
        context.setVariable("date", getCurrentDate());
        context.setVariable("type", "missionStage");
        context.setVariable("code",  getCode(missionStage.getId(), missionStage.getUser()));
        context.setVariable("ref", missionStage.getId());
        context.setVariable("genre", getGender(missionStage.getUser().getGender()));
        context.setVariable("date_deb", missionStage.getDateDebut());
        context.setVariable("date_fin", missionStage.getDateFin());
        context.setVariable("montant", missionStage.getSoutien().getMontant());
        context.setVariable("username", missionStage.getUser().getNom() + " " + missionStage.getUser().getPrenom());
        String entite_recherche = getEntiteRecherche(missionStage.getUser());
        String directeur = entite_recherche.substring(0, 1).toUpperCase().equals("F") ? "doyen" : "directeur";
        context.setVariable("entite_recherche", entite_recherche);
        context.setVariable("directeur", directeur);
        context.setVariable("objet", getObjetType(DemandeType.MISSION_STAGE));

        return context;
    }

    private Context getContextMissionStagePDFDemande(Long id){
        MissionStage missionStage = missionStageService.getById(id);
        Context context = new Context();
        context.setVariable("mission", missionStage);

        return context;
    }

    private Context getContextManifestationPDFDemande(Long id){
        Manifestation manifestation = manifestationService.getById(id);
        Context context = new Context();
        context.setVariable("manifestation", manifestation);

        return context;
    }

    private Context getContextManifestationPDF(Long id){
        Manifestation manifestation = manifestationService.getById(id);
        Context context = new Context();
        context.setVariable("date", getCurrentDate());
        context.setVariable("type", "manifestation");
        context.setVariable("code", getCode(manifestation.getId(), manifestation.getUser()));
        context.setVariable("ref", manifestation.getId());
        context.setVariable("genre", getGender(manifestation.getUser().getGender()));
        context.setVariable("titre_manif", manifestation.getTitreManifestation());
        context.setVariable("lieu", manifestation.getVille());
        context.setVariable("date_deb", manifestation.getDateDebut());
        context.setVariable("date_fin", manifestation.getDateFin());
        context.setVariable("montant", manifestation.getSoutien().getMontant());
        context.setVariable("username", manifestation.getUser().getNom() + " " + manifestation.getUser().getPrenom());
        String entite_recherche = getEntiteRecherche(manifestation.getUser());
        String directeur = entite_recherche.substring(0, 1).toUpperCase().equals("F") ? "doyen" : "directeur";
        context.setVariable("entite_recherche", entite_recherche);
        context.setVariable("directeur", directeur);

        context.setVariable("objet", getObjetType(DemandeType.MANIFESTATION));

        return context;
    }

    private String loadAndFillTemplate(String template, Context context){
        return springTemplateEngine.process(template, context);
    }

    private String getGender(String gender){
        return gender.equals("male") ? "Monsieur" : "Madame";
    }

    private String getEntiteRecherche(User user){
        DoneesPro doneesPro = doneesProService.findByUser(user);
        return doneesPro.getEntiteRecherche();
    }

    private String getObjetType(DemandeType demandeType){
        return demandeType == DemandeType.MANIFESTATION ?
                "Votre demande de soutien pour participation à une manifestation scientifique" :
                "Votre demande de soutien pour un stage de recherche";
    }

    private String getCurrentDate(){
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        return targetFormat.format(new Date());
    }

    private String getCode(Long code, User user){
        DateFormat targetFormat = new SimpleDateFormat("yyyy");
        DoneesPro doneesPro = doneesProService.findByUser(user);

        return code + "/" + doneesPro.getEtablissement().getNom() + "/" + targetFormat.format(new Date());
    }


}
