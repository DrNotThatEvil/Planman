package in.wilv.planman.daytree;

import in.wilv.planman.daytree.FreeTimeDB;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class FreeTimeDBConfiguration
{
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FreeTimeDB getFreeTimeDB() {
        return new FreeTimeDB();
    }
}
