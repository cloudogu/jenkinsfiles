package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MemberRegistrationTest {

    @Mock
    private Logger log;

    @Mock
    private EntityManager em;

    @Mock
    private Event<Member> memberEventSrc;

    @InjectMocks
    private MemberRegistration memberRegistration;
    private String expectedName = "the member's name";


    @Test
    public void register() throws Exception {
        Member member = mock(Member.class);
        when(member.getName()).thenReturn(expectedName);

        memberRegistration.register(member);

        verify(log).info("Registering " + expectedName);
        verify(em).persist(member);
        verify(memberEventSrc).fire(member);
    }

}