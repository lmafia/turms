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

package im.turms.server.common.access.http.dto.response;


import com.fasterxml.jackson.annotation.JsonCreator;
import im.turms.server.common.constant.TurmsStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * @author James Chen
 * @see im.turms.service.workflow.access.http.dto.response.ErrorAttributes
 */
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Data
@FieldNameConstants
public class ResponseDTO<T> {

    private final Integer code;
    private final String reason;
    private final Date timestamp;
    @Nullable
    private final T data;

    public ResponseDTO(TurmsStatusCode turmsStatusCode, @Nullable T data) {
        this.code = turmsStatusCode.getBusinessCode();
        this.reason = turmsStatusCode.getReason();
        this.timestamp = new Date();
        this.data = data;
    }

}