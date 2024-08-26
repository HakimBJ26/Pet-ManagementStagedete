package com.example.PetgoraBackend.controller;

import com.example.PetgoraBackend.service.BlockchainService;
import com.example.PetgoraBackend.service.BlockchainService.CertificateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private BlockchainService blockchainService;

    // Endpoint to issue a certificate
    @PostMapping("/issue")
    public String issueCertificate(
            @RequestParam BigInteger uniqueId,
            @RequestParam String petName,
            @RequestParam String breed,
            @RequestParam String birthDate) {
        return blockchainService.issueCertificate(uniqueId, petName, breed, birthDate);
    }

    // Endpoint to verify a certificate and return its data
    @GetMapping("/verify")
    public CertificateData verifyCertificate(@RequestParam BigInteger uniqueId) {
        try {
            return blockchainService.verifyCertificate(uniqueId);
        } catch (Exception e) {
            e.printStackTrace();
            return new CertificateData(false, "", "", "", BigInteger.ZERO);
        }
    }
}
