/*
 * Copyright 2017 Anton Tananaev (anton@traccar.org)
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
package org.traccar.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.nio.charset.StandardCharsets;

public class WatchFrameDecoder extends FrameDecoder {

    public static final int MESSAGE_HEADER = 20;

    @Override
    protected Object decode(
            ChannelHandlerContext ctx, Channel channel, ChannelBuffer buf) throws Exception {

        if (buf.readableBytes() >= MESSAGE_HEADER) {
            ChannelBuffer lengthBuffer = ChannelBuffers.dynamicBuffer();
            buf.getBytes(buf.readerIndex() + MESSAGE_HEADER - 4 - 1, lengthBuffer, 4);
            int length = Integer.parseInt(lengthBuffer.toString(StandardCharsets.US_ASCII), 16) + MESSAGE_HEADER + 1;
            if (buf.readableBytes() >= length) {
                return buf.readBytes(length);
            }
        }

        return null;
    }

}
