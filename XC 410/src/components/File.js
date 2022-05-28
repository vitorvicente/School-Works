import React from "react";
import {Col, Row} from "react-bootstrap";

import {StyledContainer} from "../styles/Styles";

const File = ({title, file}) => (<StyledContainer>
    <Row>
      <Col>
        <h1> {title} </h1>
      </Col>
    </Row>

    <Row>
      <Col>
        <iframe title={title}
                width="100%" height="750"
                src={`https://drive.google.com/file/d/${file}/preview`}/>
      </Col>
    </Row>
  </StyledContainer>);

export default File;