import React from "react";
import Col from "react-bootstrap/Col";
import {Link} from "react-router-dom";

import {StyledButton, StyledCard} from "../styles/Styles";


const Card = ({title, icon, link}) => (<Col>
  <StyledCard>
    {icon}
    <h3>{title}</h3>
    <Link to={link}><StyledButton> Read it! </StyledButton></Link>
  </StyledCard>
</Col>);

export default Card;