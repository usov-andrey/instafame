/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableFunction;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class IntegerField extends GeneratedVaadinNumberField<IntegerField, Integer>
        implements HasSize, HasValidation, HasValueChangeMode,
        HasPrefixAndSuffix, InputNotifier, KeyNotifier, CompositionNotifier,
        HasAutocomplete, HasAutocapitalize, HasAutocorrect {

    private static final SerializableFunction<String, Integer> PARSER = valueFromClient -> valueFromClient == null
            || valueFromClient.isEmpty() ? null
            : Integer.parseInt(valueFromClient);

    private static final SerializableFunction<Integer, String> FORMATTER = valueFromModel -> valueFromModel == null
            ? ""
            : valueFromModel.toString();

    private ValueChangeMode currentMode;

    /**
     * Constructs an empty {@code NumberField}.
     */
    public IntegerField() {
        super(null, null, String.class, PARSER, FORMATTER);
        setValueChangeMode(ValueChangeMode.ON_CHANGE);
    }

    /**
     * Constructs an empty {@code NumberField} with the given label.
     *
     * @param label the text to set as the label
     */
    public IntegerField(String label) {
        this();
        setLabel(label);
    }

    /**
     * Constructs an empty {@code NumberField} with the given label and
     * placeholder text.
     *
     * @param label       the text to set as the label
     * @param placeholder the placeholder text to set
     */
    public IntegerField(String label, String placeholder) {
        this(label);
        setPlaceholder(placeholder);
    }

    /**
     * Constructs an empty {@code NumberField} with a value change listener.
     *
     * @param listener the value change listener
     * @see #addValueChangeListener(ValueChangeListener)
     */
    public IntegerField(
            ValueChangeListener<? super ComponentValueChangeEvent<IntegerField, Integer>> listener) {
        this();
        addValueChangeListener(listener);
    }

    /**
     * Constructs an empty {@code NumberField} with a value change listener
     * and a label.
     *
     * @param label    the text to set as the label
     * @param listener the value change listener
     * @see #setLabel(String)
     * @see #addValueChangeListener(ValueChangeListener)
     */
    public IntegerField(String label,
                        ValueChangeListener<? super ComponentValueChangeEvent<IntegerField, Integer>> listener) {
        this(label);
        addValueChangeListener(listener);
    }

    /**
     * Constructs a {@code NumberField} with a value change listener, a label
     * and an initial value.
     *
     * @param label        the text to set as the label
     * @param initialValue the initial value
     * @param listener     the value change listener
     * @see #setLabel(String)
     * @see #setValue(Object)
     * @see #addValueChangeListener(ValueChangeListener)
     */
    public IntegerField(String label, Integer initialValue,
                        ValueChangeListener<? super ComponentValueChangeEvent<IntegerField, Integer>> listener) {
        this(label);
        setValue(initialValue);
        addValueChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default value is {@link ValueChangeMode#ON_CHANGE}.
     */
    @Override
    public ValueChangeMode getValueChangeMode() {
        return currentMode;
    }

    @Override
    public void setValueChangeMode(ValueChangeMode valueChangeMode) {
        currentMode = valueChangeMode;
        setSynchronizedEvent(
                ValueChangeMode.eventForMode(valueChangeMode, "value-changed"));
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessageString();
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
    }

    @Override
    public boolean isInvalid() {
        return isInvalidBoolean();
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
    }

    /**
     * String used for the label element.
     *
     * @return the {@code label} property from the webcomponent
     */
    public String getLabel() {
        return getLabelString();
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
    }

    /**
     * The maximum value of the field.
     *
     * @return the {@code max} property from the webcomponent
     */
    public double getMax() {
        return super.getMaxDouble();
    }

    @Override
    public void setMax(double max) {
        super.setMax(max);
    }

    /**
     * The minimum value of the field.
     *
     * @return the {@code min} property from the webcomponent
     */
    public double getMin() {
        return super.getMinDouble();
    }

    @Override
    public void setMin(double min) {
        super.setMin(min);
    }

    /**
     * Specifies the allowed number intervals of the field.
     *
     * @return the {@code step} property from the webcomponent
     */
    public double getStep() {
        return super.getStepDouble();
    }

    @Override
    public void setStep(double step) {
        super.setStep(step);
    }

    @Override
    public void setHasControls(boolean hasControls) {
        super.setHasControls(hasControls);
    }

    /**
     * Set to true to display value increase/decrease controls.
     *
     * @return the {@code hasControls} property from the webcomponent
     */
    public boolean hasControls() {
        return super.hasControlsBoolean();
    }

    /**
     * A hint to the user of what can be entered in the component.
     *
     * @return the {@code placeholder} property from the webcomponent
     */
    public String getPlaceholder() {
        return getPlaceholderString();
    }

    @Override
    public void setPlaceholder(String placeholder) {
        super.setPlaceholder(placeholder);
    }

    /**
     * Specify that this control should have input focus when the page loads.
     *
     * @return the {@code autofocus} property from the webcomponent
     */
    public boolean isAutofocus() {
        return isAutofocusBoolean();
    }

    @Override
    public void setAutofocus(boolean autofocus) {
        super.setAutofocus(autofocus);
    }

    /**
     * Maximum number of characters (in Unicode code points) that the user can
     * enter.
     *
     * @return the {@code maxlength} property from the webcomponent
     */
    public int getMaxLength() {
        return (int) getMaxlengthDouble();
    }

    /**
     * Maximum number of characters (in Unicode code points) that the user can
     * enter.
     *
     * @param maxLength the maximum length
     */
    public void setMaxLength(int maxLength) {
        super.setMaxlength(maxLength);
    }

    /**
     * Minimum number of characters (in Unicode code points) that the user can
     * enter.
     *
     * @return the {@code minlength} property from the webcomponent
     */
    public int getMinLength() {
        return (int) getMinlengthDouble();
    }

    /**
     * Minimum number of characters (in Unicode code points) that the user can
     * enter.
     *
     * @param minLength the minimum length
     */
    public void setMinLength(int minLength) {
        super.setMinlength(minLength);
    }

    /**
     * When set to <code>true</code>, user is prevented from typing a value that
     * conflicts with the given {@code pattern}.
     *
     * @return the {@code preventInvalidInput} property from the webcomponent
     */
    public boolean isPreventInvalidInput() {
        return isPreventInvalidInputBoolean();
    }

    @Override
    public void setPreventInvalidInput(boolean preventInvalidInput) {
        super.setPreventInvalidInput(preventInvalidInput);
    }

    /**
     * A regular expression that the value is checked against. The pattern must
     * match the entire value, not just some subset.
     *
     * @return the {@code pattern} property from the webcomponent
     */
    public String getPattern() {
        return getPatternString();
    }

    @Override
    public void setPattern(String pattern) {
        super.setPattern(pattern);
    }

    /**
     * Message to show to the user when validation fails.
     *
     * @return the {@code title} property from the webcomponent
     */
    public String getTitle() {
        return super.getTitleString();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    /**
     * Specifies if the field value gets automatically selected when
     * the field gains focus.
     *
     * @return <code>true</code> if autoselect is active, <code>false</code>
     * otherwise
     */
    public boolean isAutoselect() {
        return super.isAutoselectBoolean();
    }

    /**
     * Set to <code>true</code> to always have the field value automatically
     * selected when the field gains focus, <code>false</code> otherwise.
     *
     * @param autoselect <code>true</code> to set auto select on,
     *                   <code>false</code> otherwise
     */
    @Override
    public void setAutoselect(boolean autoselect) {
        super.setAutoselect(autoselect);
    }

    /**
     * Gets the visibility state of the button which clears the number field.
     *
     * @return <code>true</code> if the button is visible, <code>false</code>
     * otherwise
     */
    public boolean isClearButtonVisible() {
        return isClearButtonVisibleBoolean();
    }

    /**
     * Set to <code>false</code> to hide the clear button which clears the number
     * field.
     *
     * @param clearButtonVisible <code>true</code> to set the button visible,
     *                           <code>false</code> otherwise
     */
    @Override
    public void setClearButtonVisible(boolean clearButtonVisible) {
        super.setClearButtonVisible(clearButtonVisible);
    }

    /**
     * Returns the value that represents an empty value.
     */
    @Override
    public Integer getEmptyValue() {
        return null;
    }

    /**
     * Returns the current value of the number field. By default, the empty
     * number field will return {@code null} .
     *
     * @return the current value.
     */
    @Override
    public Integer getValue() {
        return super.getValue();
    }

    /**
     * Sets the value of this number field. If the new value is not equal to
     * {@code getValue()}, fires a value change event.
     *
     * @param value the new value
     */
    @Override
    public void setValue(Integer value) {
        super.setValue(value);
    }

}
