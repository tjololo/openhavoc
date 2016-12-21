package contracts.pods.api

/**
 * Created by veg on 21.12.2016.
 */
org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/pods/test?uri=https://openshift.org:8443'
        headers {
            header('Content-Type': 'application/keres.pods.v1+json')
        }
    }
    response {
        status 200
        body("""
[
    {
        "metadata": {
                        "name": "newbase-pod-1-adfx2",
                        "selfLink": "/api/v1/namespaces/test/pods/newbase-pod-1-adfx2"
                    },
        "status": {
                    "phase": "Running"
                  }
    }
]
""")
    }
}