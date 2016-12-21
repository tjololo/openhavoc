package net.tjololo.openshift.havoc.contract;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import net.tjololo.openshift.havoc.api.PodsController;
import net.tjololo.openshift.havoc.connector.kubernetes.KubernetesDiscovery;
import net.tjololo.openshift.havoc.connector.kubernetes.contracts.Item;
import net.tjololo.openshift.havoc.connector.kubernetes.contracts.KubeResponse;
import net.tjololo.openshift.havoc.connector.kubernetes.contracts.Metadata;
import net.tjololo.openshift.havoc.connector.kubernetes.contracts.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by veg on 21.12.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PodsApiBase {
    @Mock
    private KubernetesDiscovery kubernetesDiscovery;

    @Before
    public void setup() {
        when(kubernetesDiscovery.listPods("https://10.2.2.2:8443", "empty")).thenReturn(new KubeResponse("PodList", Collections.emptyList()));
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(new Metadata("test-pod-1-adfx2", "/api/v1/namespaces/test/pods/test-pod-1-adfx2"), new Status("Running")));
        items.add(new Item(new Metadata("test-pod-1-build", "/api/v1/namespaces/test/pods/test-pod-1-adfx2"), new Status("Failed")));
        ArrayList<Item> items2 = new ArrayList<>();
        items2.add(new Item(new Metadata("newbase-pod-1-adfx2", "/api/v1/namespaces/test/pods/newbase-pod-1-adfx2"), new Status("Running")));
        items2.add(new Item(new Metadata("newbase-pod-1-build", "/api/v1/namespaces/test/pods/newbase-pod-1-build"), new Status("Failed")));
        when(kubernetesDiscovery.listPods("https://10.2.2.2:8443", "test")).thenReturn(new KubeResponse("PodList", items));
        when(kubernetesDiscovery.listPods("https://openshift.org:8443", "test")).thenReturn(new KubeResponse("PodList", items2));
        when(kubernetesDiscovery.killPod("https://10.2.2.2:8443", "test", "ok-pod-1")).thenReturn(true);
        when(kubernetesDiscovery.killPod("https://10.2.2.2:8443", "test", "fail-pod-1")).thenReturn(false);
        when(kubernetesDiscovery.killPod("https://openshift.org:8443", "other", "other-pod-1")).thenReturn(true);
        RestAssuredMockMvc.standaloneSetup(new PodsController(kubernetesDiscovery, "https://10.2.2.2:8443"));
    }

    @Test
    public void dummyTest_do_I_really_need_this() {
        assertThat(true, is(true));
    }
}