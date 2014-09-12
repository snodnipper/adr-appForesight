package com.livenation.foresight.graph;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import dagger.ObjectGraph;

public class InjectionInstrumentationTestCase extends InstrumentationTestCase {
    protected ObjectGraph graph;

    @Override
    public void injectInstrumentation(Instrumentation instrumentation) {
        super.injectInstrumentation(instrumentation);

        this.graph = ObjectGraph.create(new TestModule(getInstrumentation().getContext()));
        graph.inject(this);
    }
}
