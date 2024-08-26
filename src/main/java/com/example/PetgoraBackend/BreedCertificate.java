package com.example.PetgoraBackend;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class BreedCertificate extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506108e2806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80631692a9421461003b5780637cd44c231461006f575b600080fd5b61005560048036038101906100509190610506565b61008b565b604051610066959493929190610697565b60405180910390f35b6100896004803603810190610084919061052f565b6102f1565b005b600060608060606000806000808881526020019081526020016000206040518060800160405290816000820180546100c2906107f4565b80601f01602080910402602001604051908101604052809291908181526020018280546100ee906107f4565b801561013b5780601f106101105761010080835404028352916020019161013b565b820191906000526020600020905b81548152906001019060200180831161011e57829003601f168201915b50505050508152602001600182018054610154906107f4565b80601f0160208091040260200160405190810160405280929190818152602001828054610180906107f4565b80156101cd5780601f106101a2576101008083540402835291602001916101cd565b820191906000526020600020905b8154815290600101906020018083116101b057829003601f168201915b505050505081526020016002820180546101e6906107f4565b80601f0160208091040260200160405190810160405280929190818152602001828054610212906107f4565b801561025f5780601f106102345761010080835404028352916020019161025f565b820191906000526020600020905b81548152906001019060200180831161024257829003601f168201915b50505050508152602001600382015481525050905086816060015114156102a6576001816000015182602001518360400151846060015195509550955095509550506102e8565b60008060405180602001604052806000815250906040518060200160405280600081525090604051806020016040528060008152509095509550955095509550505b91939590929450565b60008060008681526020019081526020016000206003015414610349576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610340906106ff565b60405180910390fd5b60405180608001604052808481526020018381526020018281526020018581525060008086815260200190815260200160002060008201518160000190805190602001906103989291906103e6565b5060208201518160010190805190602001906103b59291906103e6565b5060408201518160020190805190602001906103d29291906103e6565b506060820151816003015590505050505050565b8280546103f2906107f4565b90600052602060002090601f016020900481019282610414576000855561045b565b82601f1061042d57805160ff191683800117855561045b565b8280016001018555821561045b579182015b8281111561045a57825182559160200191906001019061043f565b5b509050610468919061046c565b5090565b5b8082111561048557600081600090555060010161046d565b5090565b600061049c61049784610750565b61071f565b9050828152602081018484840111156104b457600080fd5b6104bf8482856107b2565b509392505050565b600082601f8301126104d857600080fd5b81356104e8848260208601610489565b91505092915050565b60008135905061050081610895565b92915050565b60006020828403121561051857600080fd5b6000610526848285016104f1565b91505092915050565b6000806000806080858703121561054557600080fd5b6000610553878288016104f1565b945050602085013567ffffffffffffffff81111561057057600080fd5b61057c878288016104c7565b935050604085013567ffffffffffffffff81111561059957600080fd5b6105a5878288016104c7565b925050606085013567ffffffffffffffff8111156105c257600080fd5b6105ce878288016104c7565b91505092959194509250565b6105e38161079c565b82525050565b60006105f482610780565b6105fe818561078b565b935061060e8185602086016107c1565b61061781610884565b840191505092915050565b600061062f602f8361078b565b91507f436572746966696361746520616c72656164792065786973747320776974682060008301527f7468697320756e697175652049442e00000000000000000000000000000000006020830152604082019050919050565b610691816107a8565b82525050565b600060a0820190506106ac60008301886105da565b81810360208301526106be81876105e9565b905081810360408301526106d281866105e9565b905081810360608301526106e681856105e9565b90506106f56080830184610688565b9695505050505050565b6000602082019050818103600083015261071881610622565b9050919050565b6000604051905081810181811067ffffffffffffffff8211171561074657610745610855565b5b8060405250919050565b600067ffffffffffffffff82111561076b5761076a610855565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b60008115159050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156107df5780820151818401526020810190506107c4565b838111156107ee576000848401525b50505050565b6000600282049050600182168061080c57607f821691505b602082108114156108205761081f610826565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b61089e816107a8565b81146108a957600080fd5b5056fea264697066735822122066ad3bf927204ffb65f723738d38ab9dfbf39cb8ca8ae815e1ff9b5c838ca46364736f6c63430008000033\r\n"
            + "\r\n";

    private static String librariesLinkedBinary;

    public static final String FUNC_ISSUECERTIFICATE = "issueCertificate";

    public static final String FUNC_VERIFYCERTIFICATE = "verifyCertificate";

    @Deprecated
    protected BreedCertificate(String contractAddress, Web3j web3j, Credentials credentials,
                               BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BreedCertificate(String contractAddress, Web3j web3j, Credentials credentials,
                               ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BreedCertificate(String contractAddress, Web3j web3j,
                               TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BreedCertificate(String contractAddress, Web3j web3j,
                               TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> issueCertificate(BigInteger _uniqueID,
                                                                   String _petName, String _breed, String _birthDate) {
        final Function function = new Function(
                FUNC_ISSUECERTIFICATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_uniqueID),
                        new org.web3j.abi.datatypes.Utf8String(_petName),
                        new org.web3j.abi.datatypes.Utf8String(_breed),
                        new org.web3j.abi.datatypes.Utf8String(_birthDate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<Boolean, String, String, String, BigInteger>> verifyCertificate(
            BigInteger _uniqueID) {
        final Function function = new Function(FUNC_VERIFYCERTIFICATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_uniqueID)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<Boolean, String, String, String, BigInteger>>(function,
                new Callable<Tuple5<Boolean, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple5<Boolean, String, String, String, BigInteger> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<Boolean, String, String, String, BigInteger>(
                                (Boolean) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    @Deprecated
    public static BreedCertificate load(String contractAddress, Web3j web3j,
                                        Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BreedCertificate(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BreedCertificate load(String contractAddress, Web3j web3j,
                                        TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BreedCertificate(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BreedCertificate load(String contractAddress, Web3j web3j,
                                        Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BreedCertificate(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BreedCertificate load(String contractAddress, Web3j web3j,
                                        TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BreedCertificate(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BreedCertificate> deploy(Web3j web3j, Credentials credentials,
                                                      ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BreedCertificate.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BreedCertificate> deploy(Web3j web3j, Credentials credentials,
                                                      BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BreedCertificate.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<BreedCertificate> deploy(Web3j web3j,
                                                      TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BreedCertificate.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BreedCertificate> deploy(Web3j web3j,
                                                      TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BreedCertificate.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
