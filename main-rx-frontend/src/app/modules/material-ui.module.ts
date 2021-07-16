import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';

const MATERIAL_COMPONENTS = [
  MatButtonModule,
  MatDividerModule,
  MatIconModule,
  MatTableModule,
  MatCheckboxModule,
];

@NgModule({
  declarations: [],
  imports: [ CommonModule, ...MATERIAL_COMPONENTS ],
  exports: [ ...MATERIAL_COMPONENTS ],
})
export class MaterialUiModule { }
