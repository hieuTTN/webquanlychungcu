import logomini from '../../assest/images/logomini.svg'
import { useState, useEffect } from 'react'
import { Parser } from "html-to-react";
import ReactPaginate from 'react-paginate';
import {toast } from 'react-toastify';
import Select from 'react-select';
import {getMethod, postMethodPayload} from '../../services/request';
import Swal from 'sweetalert2'
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import * as React from 'react';



function TaiKhoan(){
    const [value, setValue] = React.useState(0);
    useEffect(()=>{
        if (window.location.hash === '#lichtiem') {
            setValue(1); 
        }
    }, []);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

  

    return(
     <div className='container-fluid'>
        <div className='container-web acctaikhoan'>
        </div>
     </div>
    );
}
  
export default TaiKhoan;
