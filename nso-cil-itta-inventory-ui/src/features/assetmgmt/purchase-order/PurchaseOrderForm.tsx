import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { PURCHASE_ORDER_CRITICALITY_OPTIONS, PurchaseOrderCriticality } from './types';
import { validatePurchaseOrder, cleanPurchaseOrderValues } from './schema';
import { PURCHASEORDER_DEFAULTS } from './types';
import type { PurchaseOrderFormValues, PurchaseOrderInput } from './types';

interface Props {
  initial?: PurchaseOrderFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PurchaseOrderInput) => void;
  onCancel?: () => void;
}

export function PurchaseOrderForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PurchaseOrderFormValues>({ ...PURCHASEORDER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PurchaseOrderFormValues>(key: K, value: PurchaseOrderFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validatePurchaseOrder(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPurchaseOrderValues(values as Record<string, unknown>) as unknown as PurchaseOrderInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Purchase Order form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Po Number" htmlFor="poNumber" error={errors.poNumber} required={true}>
        <Input
          id="poNumber"
          value={values.poNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('poNumber', v)}
        />
      </FormField>
      <FormField label="Vendor Id" htmlFor="vendorId" error={errors.vendorId} required={true}>
        <Input
          id="vendorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vendorId ?? ''}
          onChange={(v) => set('vendorId', v)}
        />
      </FormField>
      <FormField label="Ordered On" htmlFor="orderedOn" error={errors.orderedOn} required={true}>
        <DateInput
          id="orderedOn"
          value={values.orderedOn ?? ''}
          onChange={(v) => set('orderedOn', v)}
        />
      </FormField>
      <FormField label="Total Amount" htmlFor="totalAmount" error={errors.totalAmount} required={true}>
        <NumberInput
          id="totalAmount"
          value={values.totalAmount}
          step={0.0001}
          onChange={(v) => set('totalAmount', v)}
        />
      </FormField>
      <FormField label="Currency" htmlFor="currency" error={errors.currency} required={false}>
        <Input
          id="currency"
          value={values.currency ?? ''}
          maxLength={255}
          onChange={(v) => set('currency', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={PURCHASE_ORDER_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as PurchaseOrderCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
