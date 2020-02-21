package br.com.cvc.corp.hotelaria.test.bean;


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.cvc.corp.hotelaria.model.Price;

public class PriceTest {
    
    private static final double COMMISSION = 0.70;
    private static final Long DIFFDAYS = 1l;
    private static final Integer ADULTS = 1;
    private static final Integer CHILDS = 1;
    
    BigDecimal pricePerDayAdult = new BigDecimal(BigDecimal.valueOf(100.01).doubleValue() * DIFFDAYS);
    BigDecimal pricePerDayChild = new BigDecimal(BigDecimal.valueOf(50.01).doubleValue() * DIFFDAYS);

    @Test
    public void calculateTotalPrice() {
        
        Price price = new Price();
        price.setAdult(pricePerDayAdult);
        price.setChild(pricePerDayChild);
        
        BigDecimal priceKickbackAdult = new BigDecimal((price.getAdult().doubleValue() * ADULTS) / COMMISSION);
        
        BigDecimal priceKickbackChilds = new BigDecimal((price.getChild().doubleValue() * CHILDS) / COMMISSION);
        
        BigDecimal soma = priceKickbackAdult.add(priceKickbackChilds);
        BigDecimal total = new BigDecimal(parteInteira(soma.doubleValue()));
        Assert.assertEquals(BigDecimal.valueOf(215), total);
    }

    /*@Test
    public void calculateDetailsByChild() {
        Price price = new Price();
        price.setChild(BigDecimal.valueOf(50));
        BigDecimal total = price.calcChildVal(5L, 1L);
        Assert.assertEquals(BigDecimal.valueOf(250L), total);
    }

    @Test
    public void calculateDetailsByAdult() {
        Price price = new Price();
        BigDecimal total = price.calcAdultVal(5L, 1L);
        Assert.assertNull(total);
    }

    @Test
    public void notecalculateDetailsByChild() {
        Price price = new Price();
        BigDecimal total = price.calcChildVal(5L, 1L);
        Assert.assertNull(total);
    }*/
    
    double parteInteira (double valor) {
        if (valor == 0.0) {
            return Math.floor (valor);
        } else {
            return Math.ceil (valor);
        }
    }
}
