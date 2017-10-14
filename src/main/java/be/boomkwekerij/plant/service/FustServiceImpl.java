package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.FustMemory;
import be.boomkwekerij.plant.dao.repository.FustDAO;
import be.boomkwekerij.plant.dao.repository.FustDAOImpl;
import be.boomkwekerij.plant.mapper.FustMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.model.repository.Fust;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.validator.FustValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FustServiceImpl implements FustService {

    private FustDAO fustDAO = new FustDAOImpl();
    private FustMemory fustMemory = MemoryDatabase.getFustMemory();

    private FustMapper fustMapper = new FustMapper();
    private FustValidator fustValidator = new FustValidator();

    private CustomerService customerService = new CustomerServiceImpl();

    public CrudsResult createFust(FustDTO fustDTO) {
        FustOverviewDTO totalFust = new FustOverviewDTO();
        SearchResult<FustOverviewDTO> totalFustSearchResult = getFustOverviewFromCustomer(fustDTO.getCustomer().getId());
        if (totalFustSearchResult.isSuccess() && totalFustSearchResult.getFirst() != null) {
            totalFust = totalFustSearchResult.getFirst();
        }

        CrudsResult validateResult = validateFust(fustDTO, totalFust);
        if (validateResult.isError()) {
            return validateResult;
        }

        Fust fust = fustMapper.mapDTOToDAO(fustDTO);

        CrudsResult createResult = fustDAO.persist(fust);
        if (createResult.isSuccess()) {
            fustMemory.createFust(fust);
        }

        return createResult;
    }

    @Override
    public SearchResult<FustDTO> getFustFromCustomer(String customerId) {
        SearchResult<Fust> searchResult = fustMemory.getFustFromCustomer(customerId);
        if (searchResult.isSuccess()) {
            List<FustDTO> fustFromCustomer = new ArrayList<>();
            for (Fust fust : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(fust.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<FustDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                FustDTO fustDTO = fustMapper.mapDAOToDTO(fust, customer);
                fustFromCustomer.add(fustDTO);
            }
            fustFromCustomer.sort(Comparator.comparing(FustDTO::getDatum));
            return new SearchResult<FustDTO>().success(fustFromCustomer);
        }
        return new SearchResult<FustDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustOverviewDTO> getAllFustOverviews() {
        SearchResult<CustomerDTO> searchResult = customerService.getAllCustomers();
        if (searchResult.isSuccess()) {
            List<FustOverviewDTO> allFusts = new ArrayList<>();
            for (CustomerDTO customer : searchResult.getResults()) {
                SearchResult<FustOverviewDTO> fustOverviewResult = getFustOverviewFromCustomer(customer.getId());
                if (fustOverviewResult.isError()) {
                    return new SearchResult<FustOverviewDTO>().error(fustOverviewResult.getMessages());
                }
                if (fustOverviewResult.getFirst() != null) {
                    allFusts.add(fustOverviewResult.getFirst());
                }
            }
            return new SearchResult<FustOverviewDTO>().success(allFusts);
        }
        return new SearchResult<FustOverviewDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustOverviewDTO> getFustOverviewFromCustomer(String customerId) {
        SearchResult<Fust> searchResult = fustMemory.getFustFromCustomer(customerId);
        if (searchResult.isSuccess()) {
            FustOverviewDTO fustOverviewFromCustomer = null;
            for (Fust fust : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(fust.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<FustOverviewDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                FustDTO fustDTO = fustMapper.mapDAOToDTO(fust, customer);
                if (fustOverviewFromCustomer == null) {
                    fustOverviewFromCustomer = new FustOverviewDTO();
                }
                fustOverviewFromCustomer.setCustomer(customer);
                fustOverviewFromCustomer.addLageKisten(fustDTO.getLageKisten());
                fustOverviewFromCustomer.addHogeKisten(fust.getHogeKisten());
                fustOverviewFromCustomer.addPalletBodem(fust.getPalletBodem());
                fustOverviewFromCustomer.addBoxPallet(fust.getBoxPallet());
                fustOverviewFromCustomer.addHalveBox(fust.getHalveBox());
                fustOverviewFromCustomer.addFerroPalletKlein(fust.getFerroPalletKlein());
                fustOverviewFromCustomer.addFerroPalletGroot(fust.getFerroPalletGroot());
                fustOverviewFromCustomer.addKarrenEnBorden(fust.getKarrenEnBorden());
                fustOverviewFromCustomer.addDiverse(fust.getDiverse());
            }
            return new SearchResult<FustOverviewDTO>().success(Collections.singletonList(fustOverviewFromCustomer));
        }
        return new SearchResult<FustOverviewDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<FustOverviewDTO> getFustOverviewFromCustomerWithName(String customerName) {
        SearchResult<CustomerDTO> allCustomersWithNameResult = customerService.getAllCustomers(customerName);
        if (allCustomersWithNameResult.isSuccess()) {
            List<FustOverviewDTO> allFustsFromCustomerWithName = new ArrayList<>();
            for (CustomerDTO customer : allCustomersWithNameResult.getResults()) {
                SearchResult<FustOverviewDTO> fustFromCustomerResult = getFustOverviewFromCustomer(customer.getId());
                if (fustFromCustomerResult.isSuccess()) {
                    allFustsFromCustomerWithName.addAll(fustFromCustomerResult.getResults());
                } else {
                    return new SearchResult<FustOverviewDTO>().error(fustFromCustomerResult.getMessages());
                }
            }
            return new SearchResult<FustOverviewDTO>().success(allFustsFromCustomerWithName);
        }
        return new SearchResult<FustOverviewDTO>().error(allCustomersWithNameResult.getMessages());
    }

    private CrudsResult validateFust(FustDTO newFust, FustOverviewDTO currentFust) {
        List<String> validationResult = fustValidator.validate(newFust, currentFust);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(newFust.getId());
    }
}
