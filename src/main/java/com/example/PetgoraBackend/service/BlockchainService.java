package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.BreedCertificate;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class BlockchainService {

    private final Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
    private final Credentials credentials = Credentials.create("0x23b1e617b41023e7bcae212ad934d5618cfc159628b0066d6b865799c3bcb985");
    private final String contractAddress = "0x79931b5f386f63a69cef8033de56c36fc3633c79";

    @Autowired
    private PetRepo petRepository;

    public String issueCertificate(BigInteger uniqueId, String petName, String breed, String birthDate) {
        try {
            DefaultGasProvider gasProvider = new DefaultGasProvider() {
                @Override
                public BigInteger getGasLimit(String contractFunc) {
                    return BigInteger.valueOf(8000000); // Set a higher gas limit
                }
            };

            BreedCertificate contract = BreedCertificate.load(
                    contractAddress, web3j, credentials, gasProvider);

            // Call the smart contract function to issue a certificate
            TransactionReceipt receipt = contract.issueCertificate(uniqueId, petName, breed, birthDate).send();
            String transactionHash = receipt.getTransactionHash();

            // Update the Pet entity with the blockchain certificate hash
            Optional<Pet> petOptional = petRepository.findById(uniqueId.intValue());
            if (petOptional.isPresent()) {
                Pet pet = petOptional.get();
                pet.setBlockchainCert(transactionHash);
                petRepository.save(pet);
            } else {
                return "Pet with ID " + uniqueId + " not found.";
            }

            return "Certificate issued successfully. Transaction Hash: " + transactionHash;
        } catch (Exception e) {
            return "Failed to issue certificate: " + e.getMessage();
        }
    }

    // Function to verify a certificate and return its data
    public CertificateData verifyCertificate(BigInteger uniqueId) throws Exception {
        BreedCertificate contract = BreedCertificate.load(
                contractAddress, web3j, credentials, new DefaultGasProvider());

        // Call the smart contract function to verify a certificate
        Tuple5<Boolean, String, String, String, BigInteger> result = contract.verifyCertificate(uniqueId).send();

        boolean exists = result.component1();
        if (exists) {
            String petName = result.component2();
            String breed = result.component3();
            String birthDate = result.component4();
            BigInteger returnedId = result.component5();
            return new CertificateData(true, petName, breed, birthDate, returnedId);
        } else {
            return new CertificateData(false, "", "", "", BigInteger.ZERO);
        }
    }

    // Helper class to store certificate data
    public static class CertificateData {
        public boolean exists;
        public String petName;
        public String breed;
        public String birthDate;
        public BigInteger uniqueId;

        public CertificateData(boolean exists, String petName, String breed, String birthDate, BigInteger uniqueId) {
            this.exists = exists;
            this.petName = petName;
            this.breed = breed;
            this.birthDate = birthDate;
            this.uniqueId = uniqueId;
        }
    }
}
