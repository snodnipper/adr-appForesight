package com.livenation.foresight.graph.presenters;

/**
 * In Foresight, an UpdatablePresenter is a class which contains one
 * or more stateful values contained in public final ReplaySubjects.
 * <p/>
 * When writing tests for presenters, mocks/stubs should be created
 * by subclassing whatever dependencies are needed to initialize a
 * presenter and overriding their update methods.
 */
public interface UpdatablePresenter extends Presenter {
    /**
     * The update point for all presenters that have long-term persistent
     * state encapsulated in subjects. In order to create a mock version
     * of a presenter, this method should be overridden and the mock
     * subclass should populate the presenter's subject with a state.
     */
    void update();
}
