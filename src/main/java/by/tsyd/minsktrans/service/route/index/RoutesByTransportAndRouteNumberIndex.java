package by.tsyd.minsktrans.service.route.index;

import by.tsyd.minsktrans.domain.Route;
import by.tsyd.minsktrans.domain.TransportType;
import by.tsyd.minsktrans.util.LazyValue;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Dmitry Tsydzik
 * @since Date: 19.02.14.
 */
public class RoutesByTransportAndRouteNumberIndex implements BiFunction<TransportType, String, List<Route>> {

    private final Supplier<Map<TransportType, Map<String, List<Route>>>> index;

    public RoutesByTransportAndRouteNumberIndex(Supplier<List<Route>> routeListSupplier) {
        index = new LazyValue<>(() -> routeListSupplier.get().stream()
                .collect(Collectors.groupingBy(Route::getTransport, Collectors.groupingBy(Route::getRouteNumber))));
    }

    @Override
    public List<Route> apply(TransportType transport, String routeNumber) {
        Map<String, List<Route>> routeNumberRoutesIndex = index.get().get(transport);
        return routeNumberRoutesIndex == null ? null : routeNumberRoutesIndex.get(routeNumber);
    }
}
