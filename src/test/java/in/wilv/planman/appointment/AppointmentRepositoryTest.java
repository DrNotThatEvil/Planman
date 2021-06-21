package in.wilv.planman.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class
})
public class AppointmentRepositoryTest
{

    @Autowired
    private AppointmentRepository appointmentRepository;

}
