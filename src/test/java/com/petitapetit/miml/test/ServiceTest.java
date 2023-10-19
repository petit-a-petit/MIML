package com.petitapetit.miml.test;

import com.petitapetit.miml.test.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class) // Mockito 기능 사용
@ActiveProfiles(TestProfile.TEST)
@Disabled
public class ServiceTest {
}
