/**
 * Copyright 2011 Google Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.zestic.coin.core;

/**
 * Thrown when something goes wrong with storing a block. Examples: out of disk
 * space.
 */
public class BlockStoreException extends Exception {

    public BlockStoreException(String message) {
        super(message);
    }

    public BlockStoreException(Throwable t) {
        super(t);
    }
}
