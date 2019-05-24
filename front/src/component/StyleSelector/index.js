import { Select } from 'antd';
import React from "react";

const { Option } = Select;

export default (props) => <Select {...props}>
    <Option value={"AMERICAN"}>American</Option>
    <Option value={"EUROPEAN"}>European</Option>
</Select>