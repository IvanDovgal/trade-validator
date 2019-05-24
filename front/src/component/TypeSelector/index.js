import { Select } from 'antd';
import React from "react";

const { Option } = Select;

export default (props) => <Select {...props}>
    <Option value={"Spot"}>Spot</Option>
    <Option value={"Forward"}>Forward</Option>
    <Option value={"VanillaOption"}>Option</Option>
</Select>