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
import org.web3j.tuples.generated.Tuple4;
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
    public static final String BINARY = "0x608060405234801561001057600080fd5b506107ce806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80631692a94214610046578063663b3e22146100765780637cd44c23146100a9575b600080fd5b610060600480360381019061005b919061046b565b6100c5565b60405161006d9190610596565b60405180910390f35b610090600480360381019061008b919061046b565b6100e6565b6040516100a094939291906105b1565b60405180910390f35b6100c360048036038101906100be9190610494565b6102ae565b005b60008160008084815260200190815260200160002060030154149050919050565b6000602052806000526040600020600091509050806000018054610109906106e0565b80601f0160208091040260200160405190810160405280929190818152602001828054610135906106e0565b80156101825780601f1061015757610100808354040283529160200191610182565b820191906000526020600020905b81548152906001019060200180831161016557829003601f168201915b505050505090806001018054610197906106e0565b80601f01602080910402602001604051908101604052809291908181526020018280546101c3906106e0565b80156102105780601f106101e557610100808354040283529160200191610210565b820191906000526020600020905b8154815290600101906020018083116101f357829003601f168201915b505050505090806002018054610225906106e0565b80601f0160208091040260200160405190810160405280929190818152602001828054610251906106e0565b801561029e5780601f106102735761010080835404028352916020019161029e565b820191906000526020600020905b81548152906001019060200180831161028157829003601f168201915b5050505050908060030154905084565b60405180608001604052808481526020018381526020018281526020018581525060008086815260200190815260200160002060008201518160000190805190602001906102fd92919061034b565b50602082015181600101908051906020019061031a92919061034b565b50604082015181600201908051906020019061033792919061034b565b506060820151816003015590505050505050565b828054610357906106e0565b90600052602060002090601f01602090048101928261037957600085556103c0565b82601f1061039257805160ff19168380011785556103c0565b828001600101855582156103c0579182015b828111156103bf5782518255916020019190600101906103a4565b5b5090506103cd91906103d1565b5090565b5b808211156103ea5760008160009055506001016103d2565b5090565b60006104016103fc8461063c565b61060b565b90508281526020810184848401111561041957600080fd5b61042484828561069e565b509392505050565b600082601f83011261043d57600080fd5b813561044d8482602086016103ee565b91505092915050565b60008135905061046581610781565b92915050565b60006020828403121561047d57600080fd5b600061048b84828501610456565b91505092915050565b600080600080608085870312156104aa57600080fd5b60006104b887828801610456565b945050602085013567ffffffffffffffff8111156104d557600080fd5b6104e18782880161042c565b935050604085013567ffffffffffffffff8111156104fe57600080fd5b61050a8782880161042c565b925050606085013567ffffffffffffffff81111561052757600080fd5b6105338782880161042c565b91505092959194509250565b61054881610688565b82525050565b60006105598261066c565b6105638185610677565b93506105738185602086016106ad565b61057c81610770565b840191505092915050565b61059081610694565b82525050565b60006020820190506105ab600083018461053f565b92915050565b600060808201905081810360008301526105cb818761054e565b905081810360208301526105df818661054e565b905081810360408301526105f3818561054e565b90506106026060830184610587565b95945050505050565b6000604051905081810181811067ffffffffffffffff8211171561063257610631610741565b5b8060405250919050565b600067ffffffffffffffff82111561065757610656610741565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b60008115159050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156106cb5780820151818401526020810190506106b0565b838111156106da576000848401525b50505050565b600060028204905060018216806106f857607f821691505b6020821081141561070c5761070b610712565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b61078a81610694565b811461079557600080fd5b5056fea26469706673582212200adab4915dc7954bffc812d739f9b011702277630ccbb5fcd70e5fadf503c9e364736f6c63430008000033\r\n";

    private static String librariesLinkedBinary;

    public static final String FUNC_CERTIFICATES = "certificates";

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

    public RemoteFunctionCall<Tuple4<String, String, String, BigInteger>> certificates(
            BigInteger param0) {
        final Function function = new Function(FUNC_CERTIFICATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, String, BigInteger>>(function,
                new Callable<Tuple4<String, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
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

    public RemoteFunctionCall<Boolean> verifyCertificate(BigInteger _uniqueID) {
        final Function function = new Function(FUNC_VERIFYCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_uniqueID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
