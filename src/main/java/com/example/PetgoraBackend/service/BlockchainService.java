package com.example.PetgoraBackend.service;

import com.example.PetgoraBackend.BreedCertificate;
import com.example.PetgoraBackend.entity.Pet;
import com.example.PetgoraBackend.repository.PetRepo;
import io.github.cdimascio.dotenv.Dotenv;
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
    Dotenv dotenv = Dotenv.configure().load();
    private final Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
    private final Credentials credentials = Credentials.create(dotenv.get("GANACHE_PRIVATE_KEY"));
    private final String contractAddress = dotenv.get("CONTRACT_ADDRESS");

    @Autowired
    private PetRepo petRepository;

    public String issueCertificate(BigInteger uniqueId, String petName, String breed, String birthDate) {

        try {
            DefaultGasProvider gasProvider = new DefaultGasProvider() {
                @Override
                public BigInteger getGasLimit(String contractFunc) {
                    return BigInteger.valueOf(8000000);
                }
            };

            BreedCertificate contract = BreedCertificate.load(
                    contractAddress, web3j, credentials, gasProvider);

            TransactionReceipt receipt = contract.issueCertificate(uniqueId, petName, breed, birthDate).send();
            String transactionHash = receipt.getTransactionHash();

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

    public CertificateData verifyCertificate(BigInteger uniqueId) throws Exception {
        BreedCertificate contract = BreedCertificate.load(
                contractAddress, web3j, credentials, new DefaultGasProvider());

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

    public String declineCertificate(Integer uniqueId) {
        Pet pet = petRepository.findById(uniqueId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + uniqueId));

        pet.setRequestCertif(false);

        petRepository.save(pet);
        return "declined";
    }


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
