package at.bayava.olingo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by pbayer.
 */
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
@Component
public class Test {
}
