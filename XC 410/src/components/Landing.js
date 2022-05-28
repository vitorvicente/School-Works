import React from 'react';
import {Col, Row} from "react-bootstrap";
import {faBook, faBookmark, faChalkboardUser, faScroll} from "@fortawesome/free-solid-svg-icons";

import Card from "./Card"
import {StyledContainer, StyledIcon} from "../styles/Styles";


const Landing = () => (<StyledContainer>
  <Row>
    <Col>
      <h1> Pittsburgh 1919: The Iron Grip on the Steel Strike </h1>
    </Col>
  </Row>

  <Row>
    <Card title={"Gamebook"} link={"/gamebook"} icon={<StyledIcon icon={faBook}/>}/>
    <Card title={"Instructor's Manual"} link={"/im"} icon={<StyledIcon icon={faChalkboardUser}/>}/>
    <Card title={"Role Sheets"} link={"/roles"} icon={<StyledIcon icon={faScroll}/>}/>
    <Card title={"Bibliography"} link={"/bibliography"} icon={<StyledIcon icon={faBookmark}/>}/>
  </Row>
</StyledContainer>);

export default Landing;