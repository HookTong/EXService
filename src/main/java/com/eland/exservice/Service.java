/*
 * Copyright © 2016 Yan Zhenjie.
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
package com.eland.exservice;

import com.eland.exservice.exception.resolver.ExceptionResolver;
import com.eland.exservice.filter.Filter;
import com.eland.exservice.interceptor.Interceptor;
import com.eland.exservice.ssl.SSLSocketInitializer;
import com.eland.exservice.website.WebSite;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

/**
 * <p>The control of the server.</p>
 * Created by elandTong on 2018/8/21.
 */
public interface Service {

    /**
     * Service running status.
     *
     * @return return true, not return false.
     */
    boolean isRunning();

    /**
     * Start the server.
     */
    void startup();

    /**
     * Get the network address.
     */
    InetAddress getInetAddress();

    /**
     * Quit the server.
     */
    void shutdown();

    interface Builder {

        /**
         * Specified server need to monitor the ip address.
         */
        Builder inetAddress(InetAddress inetAddress);

        /**
         * Specify the port on which the server listens.
         */
        Builder port(int port);

        /**
         * Connection and response timeout.
         */
        Builder timeout(int timeout, TimeUnit timeUnit);

        /**
         * Setting up the server is based on the SSL protocol.
         */
        Builder sslContext(SSLContext sslContext);

        /**
         * Set SSLServerSocket's initializer.
         */
        Builder sslSocketInitializer(SSLSocketInitializer initializer);

        /**
         * Set request/response pair interceptor.
         */
        Builder interceptor(Interceptor interceptor);

        /**
         * Set up a website.
         */
        Builder website(WebSite webSite);

        /**
         * Register a {@link RequestHandler} for a path.
         */
        Builder registerHandler(String path, RequestHandler handler);

        /**
         * Set Handler's filter.
         */
        Builder filter(Filter filter);

        /**
         * Set the exception resolver in the request/response process.
         */
        Builder exceptionResolver(ExceptionResolver resolver);

        /**
         * Set the server listener.
         */
        Builder listener(ServiceListener listener);

        /**
         * Create a server.
         */
        Service build();
    }

    interface ServiceListener {
        /**
         * When the server is started.
         */
        void onStarted();

        /**
         * When the server stops running.
         */
        void onStopped();

        /**
         * An error occurred while starting the server.
         */
        void onError(Exception e);
    }
}
