package com.jusmore.trading.platform;

import com.jusmore.trading.platform.common.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class TradingOrdersServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

