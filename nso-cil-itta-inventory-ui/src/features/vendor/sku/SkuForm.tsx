import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SKU_CRITICALITY_OPTIONS, SkuCriticality } from './types';
import { validateSku, cleanSkuValues } from './schema';
import { SKU_DEFAULTS } from './types';
import type { SkuFormValues, SkuInput } from './types';

interface Props {
  initial?: SkuFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SkuInput) => void;
  onCancel?: () => void;
}

export function SkuForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SkuFormValues>({ ...SKU_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SkuFormValues>(key: K, value: SkuFormValues[K]) {
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
    const result = validateSku(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSkuValues(values as Record<string, unknown>) as unknown as SkuInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Sku form">
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
      <FormField label="Model Id" htmlFor="modelId" error={errors.modelId} required={true}>
        <Input
          id="modelId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.modelId ?? ''}
          onChange={(v) => set('modelId', v)}
        />
      </FormField>
      <FormField label="Sku Code" htmlFor="skuCode" error={errors.skuCode} required={true}>
        <Input
          id="skuCode"
          value={values.skuCode ?? ''}
          maxLength={255}
          onChange={(v) => set('skuCode', v)}
        />
      </FormField>
      <FormField label="List Price" htmlFor="listPrice" error={errors.listPrice} required={false}>
        <NumberInput
          id="listPrice"
          value={values.listPrice}
          step={0.0001}
          onChange={(v) => set('listPrice', v)}
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
      <FormField label="Lead Time Days" htmlFor="leadTimeDays" error={errors.leadTimeDays} required={false}>
        <NumberInput
          id="leadTimeDays"
          value={values.leadTimeDays}
          step={1}
          onChange={(v) => set('leadTimeDays', v)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
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
          options={SKU_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SkuCriticality | undefined)}
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
