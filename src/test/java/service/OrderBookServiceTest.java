package service;


import com.order.book.service.OrderBookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.order.book.dto.Order;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

@RunWith( MockitoJUnitRunner.class )
@Slf4j
public class OrderBookServiceTest {


        private final OrderBookService orderBookService;

        public OrderBookServiceTest() {
            this.orderBookService = new OrderBookService();
        }

        @Test
        public void testAddOrder() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');

            orderBookService.addOrder(order);

            TreeMap<Double, TreeMap<Long, Order>> bidMap = orderBookService.getBidMap();
            assertEquals(Collections.singletonMap(10.0, Collections.singletonMap(1L, order)), bidMap);
        }


        @Test
        public void testGetOrder() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');

            orderBookService.addOrder(order);

            assertEquals(order, orderBookService.getOrder(1L));
        }

        @Test
        public void testModifyOrderSize() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');
            Mockito.when(order.getSize()).thenReturn(20L);

            orderBookService.addOrder(order);
            orderBookService.modifyOrderSize(1L, 20L);

            assertEquals(20L, order.getSize());
        }

        @Test
        public void testGetPrice() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');

            orderBookService.addOrder(order);
            Double price = Double.parseDouble("10.0");
            assertEquals( price, orderBookService.getPrice('B', 1));
        }

        @Test
        public void testGetTotalSize() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');
            Mockito.when(order.getSize()).thenReturn(10L);

            orderBookService.addOrder(order);

            assertEquals(10L, orderBookService.getTotalSize('B', 1));
        }

        @Test
        public void testGetOrders() {
            Order order = Mockito.mock(Order.class);
            Mockito.when(order.getPrice()).thenReturn(10.0);
            Mockito.when(order.getId()).thenReturn(1L);
            Mockito.when(order.getSide()).thenReturn('B');

            orderBookService.addOrder(order);

            assertEquals(Collections.singletonList(order), orderBookService.getOrders('B'));
        }
    }


   /*
    @Test
    public void transformPence_ParametersNull() {
        log.info("transformPence_BigDecimalWitchDecimal: Begin");
        try {
            service.transformPence( null, null );
            assertTrue(false);

        } catch (ApiRequestException e) {
            log.info((e.getMessage()));
            if ( MessageError.ID_AND_PENCE_ARE_MANDATORY.getMessage().startsWith(e.getMessage()) ){ assertTrue(true); }
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void transformPence_BigDecimalWitchDecimal() {
        log.info("transformPence_ParametersNull: Begin");
        try {
            service.transformPence( new BigDecimal(222.1), "UUU_234");
            assertTrue(false);

        } catch (ApiRequestException e) {
            log.info((e.getMessage()));
            if ( MessageError.PENCE_NO_DECIMALS .getMessage().startsWith(e.getMessage()) ){ assertTrue(true); }
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }
    }

    */


