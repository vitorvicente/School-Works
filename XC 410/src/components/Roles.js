import React from 'react';
import {Col, Row} from "react-bootstrap";

import Card from "./Card"
import {StyledContainer} from "../styles/Styles";


const Roles = () => (<StyledContainer>
  <Row>
    <Col>
      <h1> Role Sheets </h1>
    </Col>
  </Row>

  <Row xl={4} lg={4} md={3} sm={2} xs={1}>
    <Card title={"Charles Strophel"} link={"/roles/anti-strike/one"}/>
    <Card title={"Albert D. Jones Jr."} link={"/roles/anti-strike/two"}/>
    <Card title={"Thomas Liggett"} link={"/roles/anti-strike/three"}/>

    <Card title={"Louis M. Walsh"} link={"/roles/indeterminate/one"}/>
    <Card title={"Roger Clark"} link={"/roles/indeterminate/two"}/>
    <Card title={"Robert O’Donnell"} link={"/roles/indeterminate/three"}/>
    <Card title={"Joseph Moore"} link={"/roles/indeterminate/four"}/>
    <Card title={"Bill Gardiner"} link={"/roles/indeterminate/five"}/>

    <Card title={"William Foster"} link={"/roles/pro-strike/one"}/>
    <Card title={"Mother Jones"} link={"/roles/pro-strike/two"}/>
    <Card title={"Walter Loan"} link={"/roles/pro-strike/three"}/>
    <Card title={"Edward Johnson"} link={"/roles/pro-strike/four"}/>
  </Row>
</StyledContainer>);

export default Roles;