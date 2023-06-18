package com.projecttypea.typea.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.projecttypea.typea.bean.Documents;
import com.projecttypea.typea.bean.DoneesPro;
import com.projecttypea.typea.bean.Manifestation;
import com.projecttypea.typea.bean.MissionStage;
import com.projecttypea.typea.dao.DocumentsDao;
import com.projecttypea.typea.dao.ManifestationDao;
import com.projecttypea.typea.dao.MissionStageDao;
import com.projecttypea.typea.dao.DoneesProDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentsService {
    @Autowired
    DocumentsDao documentsDao;

    @Autowired
    MissionStageDao missionStageDao;

    @Autowired
    ManifestationDao manifestationDao;

    @Autowired
    DoneesProDao donneproDao;

    public void deleteById(Long id) {
        documentsDao.deleteById(id);
    }

    public List<Documents> findAll() {
        return documentsDao.findAll();
    }

    public int storeDocumentMissionStage(Long missionId, MultipartFile document, MultipartFile document1,
            MultipartFile document2,
            MultipartFile document3, MultipartFile document4, MultipartFile document5, MultipartFile document6)
            throws IOException {
        if (document != null) {
            storeDocumentMission(missionId, document);
        }
        if (document1 != null) {
            storeDocumentMission(missionId, document1);
        }
        if (document2 != null) {
            storeDocumentMission(missionId, document2);
        }
        if (document3 != null) {
            storeDocumentMission(missionId, document3);
        }
        if (document4 != null) {
            storeDocumentMission(missionId, document4);
        }
        if (document5 != null) {
            storeDocumentMission(missionId, document5);
        }
        if (document6 != null) {
            storeDocumentMission(missionId, document6);
        }
        return 1;
    }

    public int storeDocumentManifestation(Long manifId, MultipartFile document, MultipartFile document1,
            MultipartFile document2,
            MultipartFile document3, MultipartFile document4, MultipartFile document5, MultipartFile document6)
            throws IOException {
        if (document != null) {
            storeDocumentManifestation(manifId, document);
        }
        if (document1 != null) {
            storeDocumentManifestation(manifId, document1);
        }
        if (document2 != null) {
            storeDocumentManifestation(manifId, document2);
        }
        if (document3 != null) {
            storeDocumentManifestation(manifId, document3);
        }
        if (document4 != null) {
            storeDocumentManifestation(manifId, document4);
        }
        if (document5 != null) {
            storeDocumentManifestation(manifId, document5);
        }
        if (document6 != null) {
            storeDocumentManifestation(manifId, document6);
        }
        return 1;
    }

    public int storeDocumentMission(Long missionId, MultipartFile document) throws IOException {
        String documentName = document.getOriginalFilename();
        Documents documents = new Documents(documentName, document.getContentType(), document.getBytes());
        MissionStage curreMissionStage = missionStageDao.getById(missionId);
        documents.setMissionstage(curreMissionStage);
        documents.setFileName(documentName);
        documents.setName(UUID.randomUUID().toString());
        documentsDao.save(documents);

        return 1;
    }

    public int storeDocumentManifestation(Long manifId, MultipartFile document) throws IOException {
        String documentName = document.getOriginalFilename();
        Documents documents = new Documents(documentName, document.getContentType(), document.getBytes());
        Manifestation currentManifestation = manifestationDao.getById(manifId);
        documents.setManifestation(currentManifestation);
        documents.setFileName(documentName);
        documents.setName(UUID.randomUUID().toString());
        documentsDao.save(documents);
        return 1;
    }

    public byte[] retrieve(String docName) {
        Documents doc = documentsDao.findByName(docName);
        byte[] docBytes = doc.getData();
        return docBytes;
    }

    public Map<String, String> findAllBDocsyMStageId(Long mStageId) {
        MissionStage currentMStage = missionStageDao.getById(mStageId);
        List<Documents> docs = new ArrayList<Documents>();
        docs = currentMStage.getDocuments();
        System.out.println(docs.size());
        HashMap<String, String> map = new HashMap<>();
        if (docs.size() > 0) {
            map.put("filecin", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(0).getName());
        }
        if (docs.size() > 1) {
            map.put("fileA", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(1).getName());
        }
        if (docs.size() > 2) {
            map.put("fileB", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(2).getName());
        }
        if (docs.size() > 3) {
            map.put("fileC", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(3).getName());
        }
        if (docs.size() > 4) {
            map.put("fileD", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(4).getName());
        }
        if (docs.size() > 5) {
            map.put("fileE", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(5).getName());
        }
        return map;
    }

    public Map<String, String> findAllBDocsyManifId(Long manifId) {
        Manifestation currentManif = manifestationDao.getById(manifId);
        List<Documents> docs = new ArrayList<Documents>();
        docs = currentManif.getDocuments();
        HashMap<String, String> map = new HashMap<>();
        if (docs.size() > 0) {
            map.put("filecin", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(0).getName());
        }
        if (docs.size() > 1) {
            map.put("fileA", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(1).getName());
        }
        if (docs.size() > 2) {
            map.put("fileB", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(2).getName());
        }
        if (docs.size() > 3) {
            map.put("fileC", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(3).getName());
        }
        if (docs.size() > 4) {
            map.put("fileD", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(4).getName());
        }
        if (docs.size() > 5) {
            map.put("fileE", "http://type-a.uca.ma/api/admin/retrievedoc/" + docs.get(5).getName());
        }
        return map;
    }

}
