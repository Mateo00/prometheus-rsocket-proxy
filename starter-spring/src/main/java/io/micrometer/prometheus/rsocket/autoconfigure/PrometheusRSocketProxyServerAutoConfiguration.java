/**
 * Copyright 2020 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.prometheus.rsocket.autoconfigure;

import io.micrometer.prometheus.rsocket.PrometheusController;
import io.micrometer.prometheus.rsocket.PrometheusControllerProperties;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * Conditionally creates a {@link PrometheusController} bean if one does not already exist.
 * Creation is dependent on the presence of {@link PrometheusRSocketProxyServerMarkerConfiguration.Marker}
 * and {@link PrometheusMeterRegistry} beans. The marker can be created through the use of the
 * {@link EnablePrometheusRSocketProxyServer} annotation.
 *
 * @author Scott Steele
 * @author Doug Saus
 */
@AutoConfiguration
@ConditionalOnClass(PrometheusMeterRegistry.class)
@ConditionalOnBean(PrometheusRSocketProxyServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(PrometheusControllerProperties.class)
@Lazy(value = false)
class PrometheusRSocketProxyServerAutoConfiguration {

  @ConditionalOnMissingBean
  @Bean
  PrometheusController prometheusController(PrometheusMeterRegistry meterRegistry, PrometheusControllerProperties controllerProperties) {
    return new PrometheusController(meterRegistry, controllerProperties);
  }

}
