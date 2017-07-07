/*
 *  Copyright 2017 Keval Patel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance wit
 *  the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 *   the specific language governing permissions and limitations under the License.
 */

package com.kevalpatel2106.rxbussample;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import rxbus.Event;
import rxbus.RxBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    static final String EVENT_CLICK = "event_click";

    public FragmentOne() {
        // Required empty public constructor
    }

    public static FragmentOne getNewInstance(){
        return new FragmentOne();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_one, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.root_view).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {
                Point point = new Point();
                point.x = (int) ev.getX();
                point.y = (int) ev.getY();

                RxBus.getDefault().post(new Event(point));
                return true;
            }
        });
    }
}
