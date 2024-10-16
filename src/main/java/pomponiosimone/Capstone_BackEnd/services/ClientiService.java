package pomponiosimone.Capstone_BackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.Cliente;
import pomponiosimone.Capstone_BackEnd.repositories.ClientiRepository;

@Service
public class ClientiService {

    @Autowired
    private ClientiRepository clientiRepository;

    //Find all
    public Page<Cliente> findAllCienti(int page, int size, String sortBy) {
           if (page > 12) page = 12;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.clientiRepository.findAll(pageable);

}
  //Find by Id
    public Cliente 

}

