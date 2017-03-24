package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.FustMemory;
import be.boomkwekerij.plant.dao.repository.FustDAO;
import be.boomkwekerij.plant.dao.repository.FustDAOImpl;
import be.boomkwekerij.plant.mapper.FustMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.repository.Fust;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.validator.FustValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FustServiceImpl implements FustService {

    private FustDAO fustDAO = new FustDAOImpl();
    private FustMemory fustMemory = MemoryDatabase.getFustMemory();

    private FustMapper fustMapper = new FustMapper();
    private FustValidator fustValidator = new FustValidator();

    private CustomerService customerService = new CustomerServiceImpl();

    public CrudsResult createFust(FustDTO fustDTO) {
        CrudsResult validateResult = validateFust(fustDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Fust fust = fustMapper.mapDTOToDAO(fustDTO);

        CrudsResult createResult;
        boolean emptyFust = fustDTO.getLageKisten() == 0
                && fustDTO.getHogeKisten() == 0
                && fustDTO.getPalletBodem() == 0
                && fustDTO.getBoxPallet() == 0
                && fustDTO.getHalveBox() == 0
                && fustDTO.getFerroPalletKlein() == 0
                && fustDTO.getFerroPalletGroot() == 0
                && fustDTO.getKarrenEnBorden() == 0
                && fustDTO.getDiverse() == 0;

        if (fustDTO.getId() != null && !fustDTO.getId().isEmpty()) {
            if (emptyFust) {
                createResult = deleteFust(fustDTO.getId());
            } else {
                createResult = fustDAO.update(fust);
            }
        } else {
            if (emptyFust) {
                createResult = new CrudsResult().success();
            } else {
                createResult = fustDAO.persist(fust);
            }
        }

        if (createResult.isSuccess() && !emptyFust) {
            fustMemory.createFust(fust);
        }

        return createResult;
    }

    @Override
    public SearchResult<FustDTO> getFust(String id) {
        SearchResult<Fust> searchResult = fustMemory.getFust(id);
        if (searchResult.isSuccess()) {
            Fust fust = searchResult.getFirst();
            if (fust != null) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(fust.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<FustDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                FustDTO fustDTO = fustMapper.mapDAOToDTO(fust, customer);
                return new SearchResult<FustDTO>().success(Collections.singletonList(fustDTO));
            }
        }
        return new SearchResult<FustDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustDTO> getAllFusts() {
        SearchResult<Fust> searchResult = fustMemory.getFusts();
        if (searchResult.isSuccess()) {
            List<FustDTO> allFusts = new ArrayList<FustDTO>();
            for (Fust fust : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(fust.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<FustDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                FustDTO fustDTO = fustMapper.mapDAOToDTO(fust, customer);
                allFusts.add(fustDTO);
            }
            return new SearchResult<FustDTO>().success(allFusts);
        }
        return new SearchResult<FustDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustDTO> getFustFromCustomer(String customerId) {
        SearchResult<Fust> searchResult = fustMemory.getFustFromCustomer(customerId);
        if (searchResult.isSuccess()) {
            List<FustDTO> allFustsFromCustomer = new ArrayList<FustDTO>();
            for (Fust fust : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(fust.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<FustDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                FustDTO fustDTO = fustMapper.mapDAOToDTO(fust, customer);
                allFustsFromCustomer.add(fustDTO);
            }
            return new SearchResult<FustDTO>().success(allFustsFromCustomer);
        }
        return new SearchResult<FustDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustDTO> getFustFromCustomerWithName(String customerName) {
        SearchResult<CustomerDTO> allCustomersWithNameResult = customerService.getAllCustomers(customerName);
        if (allCustomersWithNameResult.isSuccess()) {
            List<FustDTO> allFustsFromCustomerWithName = new ArrayList<FustDTO>();
            for (CustomerDTO customer : allCustomersWithNameResult.getResults()) {
                SearchResult<FustDTO> fustFromCustomerResult = getFustFromCustomer(customer.getId());
                if (fustFromCustomerResult.isSuccess()) {
                    allFustsFromCustomerWithName.addAll(fustFromCustomerResult.getResults());
                } else {
                    return new SearchResult<FustDTO>().error(fustFromCustomerResult.getMessages());
                }
            }
            return new SearchResult<FustDTO>().success(allFustsFromCustomerWithName);
        }
        return new SearchResult<FustDTO>().error(allCustomersWithNameResult.getMessages());
    }

    public CrudsResult deleteFust(String id) {
        CrudsResult deleteResult = fustDAO.delete(id);
        if (deleteResult.isSuccess()) {
            fustMemory.deleteFust(id);
        }
        return deleteResult;
    }

    public FustDTO getNewFustForCustomer(String customerId) {
        CustomerDTO customerDTO = getCustomer(customerId);

        SearchResult<Fust> fustFromCustomerResult = fustMemory.getFustFromCustomer(customerId);
        if (fustFromCustomerResult.isSuccess()) {
            Fust fustFromCustomer = fustFromCustomerResult.getFirst();
            if (fustFromCustomer != null) {
                return fustMapper.mapDAOToDTO(fustFromCustomer, customerDTO);
            }
        }

        FustDTO fustDTO = new FustDTO();
        fustDTO.setCustomer(customerDTO);
        fustDTO.setLageKisten(0);
        fustDTO.setHogeKisten(0);
        fustDTO.setPalletBodem(0);
        fustDTO.setBoxPallet(0);
        fustDTO.setHalveBox(0);
        fustDTO.setFerroPalletKlein(0);
        fustDTO.setFerroPalletGroot(0);
        fustDTO.setKarrenEnBorden(0);
        fustDTO.setDiverse(0);
        return fustDTO;
    }

    private CustomerDTO getCustomer(String customerId) {
        SearchResult<CustomerDTO> searchResult = customerService.getCustomer(customerId);
        if (searchResult.isSuccess()) {
            if (searchResult.getResults().size() == 1) {
                return searchResult.getFirst();
            }
        }
        throw new IllegalArgumentException("Could not find customer");
    }

    private CrudsResult validateFust(FustDTO fustDTO) {
        List<String> validationResult = fustValidator.validate(fustDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(fustDTO.getId());
    }
}
