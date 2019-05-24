import React from "react";
import {DatePicker, Form, Input, Button, Alert} from 'antd';
import TypeSelector from '../TypeSelector';
import StyleSelector from '../StyleSelector';
import DirectionSelector from '../DirectionSelector';
import { mapValues } from 'lodash';
import axios from 'axios';

export default Form.create({name: 'register'})(class extends React.Component {

    state = {
        success: null,
        errors: []
    };

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields(async (err, values) => {
            if (!err) {
                const dateKeys = ['valueDate', 'tradeDate', 'deliveryDate', 'expireDate', 'premiumDate', 'excerciseStartDate'];
                const mappedValues = mapValues(values, (value, key) => {
                    if(dateKeys.includes(key) && value)
                        return value.format('YYYY-MM-DD');
                    return value;
                });
                const { data: { errors } } = await axios.post('http://127.0.0.1:8080/trade/validation', mappedValues);
                this.setState({ errors, success: errors.length === 0 });
            }
        });
    };

    getErrorsForField = (field) => {
        const { errors } = this.state;
        const errorsMessages = errors
            .filter(({ fields }) => fields.includes(field))
            .map(({ msg }) => msg)
        if(errorsMessages.length === 0)
            return {};
        return {
            validateStatus: 'error',
            help: errorsMessages.map((msg, index) => <div key={index}>{msg}</div>)
        }

    }

    render() {
        const {getFieldDecorator, getFieldValue} = this.props.form;
        const { success } = this.state;
        const type = getFieldValue('type');
        const style = getFieldValue('style');
        return <Form>
            <Form.Item label="Type">
                {getFieldDecorator('type')(<TypeSelector/>)}
            </Form.Item>
            {
                type === 'VanillaOption' && <Form.Item label="Style">
                    {getFieldDecorator('style')(<StyleSelector/>)}
                </Form.Item>
            }
            <Form.Item label="Customer" {...this.getErrorsForField('customer')}>
                {getFieldDecorator('customer')(<Input/>)}
            </Form.Item>
            <Form.Item label="Trader" {...this.getErrorsForField('trader')}>
                {getFieldDecorator('trader')(<Input/>)}
            </Form.Item>
            <Form.Item label="ccyPair" {...this.getErrorsForField('ccyPair')}>
                {getFieldDecorator('ccyPair')(<Input/>)}
            </Form.Item>
            <Form.Item label="Legal entity" {...this.getErrorsForField('legalEntity')}>
                {getFieldDecorator('legalEntity')(<Input/>)}
            </Form.Item>
            <Form.Item label="Trade date" {...this.getErrorsForField('tradeDate')}>
                {getFieldDecorator('tradeDate')(<DatePicker/>)}
            </Form.Item>
            {
                type === 'VanillaOption' && <React.Fragment>
                    <Form.Item label="Delivery date" {...this.getErrorsForField('deliveryDate')}>
                        {getFieldDecorator('deliveryDate')(<DatePicker/>)}
                    </Form.Item>
                    <Form.Item label="Expire date" {...this.getErrorsForField('expireDate')}>
                        {getFieldDecorator('expireDate')(<DatePicker/>)}
                    </Form.Item>
                    <Form.Item label="Premium date" {...this.getErrorsForField('premiumDate')}>
                        {getFieldDecorator('premiumDate')(<DatePicker/>)}
                    </Form.Item>
                </React.Fragment>
            }
            {
                style === 'AMERICAN' && <Form.Item label="Excercise start date" {...this.getErrorsForField('excerciseStartDate')}>
                    {getFieldDecorator('excerciseStartDate')(<DatePicker/>)}
                </Form.Item>
            }
            {
                (type === 'Spot' || type === 'Forward') && <Form.Item label="Value date" {...this.getErrorsForField('valueDate')}>
                    {getFieldDecorator('valueDate')(<DatePicker/>)}
                </Form.Item>
            }
            <Form.Item label="Amount 1" {...this.getErrorsForField('amount1')}>
                {getFieldDecorator('amount1')(<Input type={"number"}/>)}
            </Form.Item>
            <Form.Item label="Amount 2" {...this.getErrorsForField('amount2')}>
                {getFieldDecorator('amount2')(<Input type={"number"}/>)}
            </Form.Item>
            <Form.Item label="Rate" {...this.getErrorsForField('rate')}>
                {getFieldDecorator('rate')(<Input type={"number"}/>)}
            </Form.Item>
            <Form.Item label="Direction" {...this.getErrorsForField('direction')}>
                {getFieldDecorator('direction')(<DirectionSelector/>)}
            </Form.Item>
            <Form.Item>
                <Button onClick={this.handleSubmit}>Validate</Button>
            </Form.Item>
            <Form.Item>
                {
                    success !== null &&
                        <Alert type={success ? "success" : "error"} message={success ? "success" : "error"}/>
                }
            </Form.Item>
        </Form>
    }
})