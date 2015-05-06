package com.zuoxiaolong;

import com.zuoxiaolong.jdbc.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 左潇龙
 * @since 5/6/2015 6:22 PM
 */
public class TestJdbc {

    @Test
    public void testJdbc() {
        Assert.assertNotNull(ConnectionFactory.getConnection());
    }

}
