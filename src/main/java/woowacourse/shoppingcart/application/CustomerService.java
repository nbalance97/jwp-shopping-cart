package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void addCustomer(final CustomerRequest customerRequest) {
        customerDao.save(customerRequest.getName(), customerRequest.getPassword());
    }

    public void deleteCustomerByName(final String customerName) {
        customerDao.deleteByName(customerName);
    }

    public Customer findCustomerByName(String customerName) {
        return customerDao.findCustomerByName(customerName);
    }

    public void editCustomerByName(final String customerName, final CustomerRequest editRequest) {
        customerDao.updateByName(customerName, editRequest.getName(), editRequest.getPassword());
    }

    public void validateNameAndPassword(String name, String password) {
        if (customerDao.existsIdByNameAndPassword(name, password)) {
            return;
        }
        throw new AuthorizationException("로그인에 실패했습니다.");
    }
}
