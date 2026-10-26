import type { LifecycleStatus, SortState } from '@/shared/types';

export interface Invoice {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  invoiceNumber: string;
  poId?: string;
  amount: number;
  dueDate?: string;
  paid?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface InvoiceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  invoiceNumber: string;
  poId?: string;
  amount: number;
  dueDate?: string;
  paid?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type InvoiceFormValues = Partial<InvoiceInput>;

export interface InvoiceUpdateInput extends Partial<InvoiceInput> {
  expectedVersion?: number;
}

export interface InvoiceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  invoiceNumber?: string;
  poId?: string;
  amountMin?: number;
  amountMax?: number;
  dueDateFrom?: string;
  dueDateTo?: string;
  paid?: boolean;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type InvoiceSort = SortState<keyof Invoice & string>;

export interface InvoiceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const INVOICE_PATH = '/api/v1/invoices';
export const INVOICE_TITLE = 'Invoice';
export const INVOICE_TITLE_PLURAL = 'Invoicees';

export const INVOICE_DEFAULTS: InvoiceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  invoiceNumber: '',
  paid: false,
};
