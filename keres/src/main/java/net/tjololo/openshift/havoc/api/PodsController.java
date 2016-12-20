package net.tjololo.openshift.havoc.api;

import net.tjololo.openshift.havoc.connector.kubernetes.KubernetesDiscovery;
import net.tjololo.openshift.havoc.connector.kubernetes.contracts.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by veg on 20.12.2016.
 */
@RestController
public class PodsController {
    private KubernetesDiscovery kubernetesDiscovery;
    private static final String BASE_URI = "https://10.2.2.2:8443";

    @Autowired
    public PodsController(KubernetesDiscovery kubernetesDiscovery) {
        this.kubernetesDiscovery = kubernetesDiscovery;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pods/{namespace}")
    public List<Item> getRunningPods(@RequestParam(value = "uri", defaultValue = BASE_URI) String baseURI, @PathVariable String namespace) {
        return kubernetesDiscovery.listPods(baseURI, namespace).getItems().stream().filter(i -> "Running".equals(i.getStatus().getPhase())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pods/kill/{namespace}/{podname}")
    public boolean killPod(@RequestParam(value = "uri", defaultValue = BASE_URI) String baseURI, @PathVariable String namespace,@PathVariable String podname) {
        return kubernetesDiscovery.killPod(baseURI, namespace, podname);
    }
}
