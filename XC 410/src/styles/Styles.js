import styled from "styled-components";
import {Container} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export const StyledCard = styled.div`
  background: #fff;
	text-align: center;
	position: relative;
	overflow: hidden;
	border-radius: 5px;
	margin: 0 10px 40px 10px;
	box-shadow: 0 10px 29px 0 rgba(68, 88, 144, 0.1);
	transition: all 0.3s ease-in-out;
	&:hover {
	    -webkit-transform: translateY(-5px);
		transform: translateY(-5px);

		
		& h3 {
			color: #d76827;
			padding-bottom: 20px;
			padding-top: 20px;
		}
	}

	
	& h3 {
		padding-bottom: 20px;
		padding-top: 20px;
	}
	
`;

export const StyledButton = styled.button`
  margin-bottom: 1.5rem;
  font-size:14px;
  font-weight:800;
  padding: 15px 25px;
  letter-spacing:1px;
  text-transform:uppercase;
  font-family:'Open Sans','Helvetica Neue',Helvetica,Arial,sans-serif;
  background-color: #d76827;
  border-color: #d76827;
  transition:all .2s;
  color: #fff;
  width:50%;
  
  &:hover {
    background-color: #482400;
    border-color: #482400;
  }
  a {
    text-decoration: none;
    color: #fff;
    
    &:hover {
      text-decoration: none;
      color: #fff;
    }
  }
`;

export const StyledContainer = styled(Container)`
  text-align: center;
  content-align: center;
  padding-top: 15rem;
  
  h1 {
    font-size: 100px;
    font-weight: 800;
    font-family:Lora,'Times New Roman',serif;
    -webkit-text-stroke-width: 0.5px;
    -webkit-text-stroke-color: #fff;
    padding-bottom: 5rem;
  }
`;

export const StyledIcon = styled(FontAwesomeIcon)`
  color: #d76827ff;
  padding-top: 20px;
  padding-bottom: 20px;
  font-size: 120px;
`;