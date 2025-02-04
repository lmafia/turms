/*
 * Copyright (C) 2019 The Turms Project
 * https://github.com/turms-im/turms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.turms.server.common.logging;

import im.turms.server.common.logging.core.logger.Logger;
import im.turms.server.common.logging.core.logger.LoggerFactory;
import im.turms.server.common.logging.core.logger.LoggerOptions;
import im.turms.server.common.logging.core.model.LogLevel;

/**
 * @author James Chen
 */
public final class CommonLogger {

    public static final int LOG_FIELD_DELIMITER = '|';

    // TODO: make configurable

    public static final Logger ADMIN_API_LOGGER = LoggerFactory.getLogger(LoggerOptions.builder()
            .filePath("@HOME/log/@SERVICE_TYPE_NAME-admin-api.log")
            .level(LogLevel.INFO)
            .build());

    public static final Logger CLIENT_API_LOGGER = LoggerFactory.getLogger(LoggerOptions.builder()
            .filePath("@HOME/log/@SERVICE_TYPE_NAME-client-api.log")
            .level(LogLevel.INFO)
            .build());

    public static final Logger NOTIFICATION_LOGGER = LoggerFactory.getLogger(LoggerOptions.builder()
            .filePath("@HOME/log/@SERVICE_TYPE_NAME-notification.log")
            .level(LogLevel.INFO)
            .build());

    private CommonLogger() {
    }

}