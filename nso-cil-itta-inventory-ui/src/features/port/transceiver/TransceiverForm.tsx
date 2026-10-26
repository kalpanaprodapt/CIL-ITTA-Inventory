import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { TRANSCEIVER_FORM_FACTOR_OPTIONS, TRANSCEIVER_LIFECYCLE_STAGE_OPTIONS, TransceiverFormFactor, TransceiverLifecycleStage } from './types';
import { validateTransceiver, cleanTransceiverValues } from './schema';
import { TRANSCEIVER_DEFAULTS } from './types';
import type { TransceiverFormValues, TransceiverInput } from './types';

interface Props {
  initial?: TransceiverFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: TransceiverInput) => void;
  onCancel?: () => void;
}

export function TransceiverForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<TransceiverFormValues>({ ...TRANSCEIVER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof TransceiverFormValues>(key: K, value: TransceiverFormValues[K]) {
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
    const result = validateTransceiver(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanTransceiverValues(values as Record<string, unknown>) as unknown as TransceiverInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Transceiver form">
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
      <FormField label="Port Id" htmlFor="portId" error={errors.portId} required={false}>
        <Input
          id="portId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.portId ?? ''}
          onChange={(v) => set('portId', v)}
        />
      </FormField>
      <FormField label="Form Factor" htmlFor="formFactor" error={errors.formFactor} required={false}>
        <Select
          id="formFactor"
          value={values.formFactor ?? ''}
          options={TRANSCEIVER_FORM_FACTOR_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('formFactor', (v || undefined) as TransceiverFormFactor | undefined)}
        />
      </FormField>
      <FormField label="Wavelength Nm" htmlFor="wavelengthNm" error={errors.wavelengthNm} required={false}>
        <NumberInput
          id="wavelengthNm"
          value={values.wavelengthNm}
          step={1}
          onChange={(v) => set('wavelengthNm', v)}
        />
      </FormField>
      <FormField label="Reach Km" htmlFor="reachKm" error={errors.reachKm} required={false}>
        <NumberInput
          id="reachKm"
          value={values.reachKm}
          step={0.0001}
          onChange={(v) => set('reachKm', v)}
        />
      </FormField>
      <FormField label="Serial Number" htmlFor="serialNumber" error={errors.serialNumber} required={false}>
        <Input
          id="serialNumber"
          value={values.serialNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('serialNumber', v)}
        />
      </FormField>
      <FormField label="Vendor Part" htmlFor="vendorPart" error={errors.vendorPart} required={false}>
        <Input
          id="vendorPart"
          value={values.vendorPart ?? ''}
          maxLength={255}
          onChange={(v) => set('vendorPart', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={TRANSCEIVER_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as TransceiverLifecycleStage | undefined)}
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
