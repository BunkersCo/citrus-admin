/*
 * Copyright 2006-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.admin.converter.endpoint;

import com.consol.citrus.model.config.core.ChannelSyncEndpointModel;
import org.springframework.stereotype.Component;

/**
 * @author Christoph Deppisch
 */
@Component
public class ChannelSyncEndpointConverter extends ChannelAdapterConverter<ChannelSyncEndpointModel> {

    @Override
    public Class<ChannelSyncEndpointModel> getSourceModelClass() {
        return ChannelSyncEndpointModel.class;
    }

    @Override
    public String getEndpointType() {
        return "channel-sync";
    }
}
