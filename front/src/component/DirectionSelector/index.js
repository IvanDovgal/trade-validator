import { Select } from 'antd';
import React from "react";

const { Option } = Select;

export default (props) => <Select {...props}>
    <Option value={"SELL"}>Sell</Option>
    <Option value={"BUY"}>Buy</Option>
</Select>