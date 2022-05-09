/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <deebendu.kumar@zestic.in>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zestic.springboot.common.activemq.backup;

import com.zestic.common.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class Channel implements Subject {

    private String title;
    private List<Observer> list = new ArrayList<>();

    @Override
    public void subscribe(Observer subscriber) {
        list.add(subscriber);
    }

    @Override
    public void unsubscribe(Observer subscriber) {
        list.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        for (Observer subscriber : list) {
            subscriber.update();
        }
    }

    @Override
    public void onMessage(Message message) {
        notifySubscribers();
    }
}
