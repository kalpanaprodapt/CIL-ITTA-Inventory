import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { OPTICAL_CHANNEL_GRID_TYPE_OPTIONS, OPTICAL_CHANNEL_CRITICALITY_OPTIONS, OpticalChannelGridType, OpticalChannelCriticality } from './types';
import { validateOpticalChannel, cleanOpticalChannelValues } from './schema';
import { OPTICALCHANNEL_DEFAULTS } from './types';
import type { OpticalChannelFormValues, OpticalChannelInput } from './types';

interface Props {
  initial?: OpticalChannelFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: OpticalChannelInput) => void;
  onCancel?: () => void;
}

export function OpticalChannelForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<OpticalChannelFormValues>({ ...OPTICALCHANNEL_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof OpticalChannelFormValues>(key: K, value: OpticalChannelFormValues[K]) {
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
    const result = validateOpticalChannel(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanOpticalChannelValues(values as Record<string, unknown>) as unknown as OpticalChannelInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Optical Channel form">
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
      <FormField label="Wavelength Nm" htmlFor="wavelengthNm" error={errors.wavelengthNm} required={true}>
        <NumberInput
          id="wavelengthNm"
          value={values.wavelengthNm}
          step={0.0001}
          onChange={(v) => set('wavelengthNm', v)}
        />
      </FormField>
      <FormField label="Channel Number" htmlFor="channelNumber" error={errors.channelNumber} required={true}>
        <NumberInput
          id="channelNumber"
          value={values.channelNumber}
          step={1}
          onChange={(v) => set('channelNumber', v)}
        />
      </FormField>
      <FormField label="Grid Type" htmlFor="gridType" error={errors.gridType} required={false}>
        <Select
          id="gridType"
          value={values.gridType ?? ''}
          options={OPTICAL_CHANNEL_GRID_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('gridType', (v || undefined) as OpticalChannelGridType | undefined)}
        />
      </FormField>
      <FormField label="Bitrate Gbps" htmlFor="bitrateGbps" error={errors.bitrateGbps} required={false}>
        <NumberInput
          id="bitrateGbps"
          value={values.bitrateGbps}
          step={0.0001}
          onChange={(v) => set('bitrateGbps', v)}
        />
      </FormField>
      <FormField label="System Id" htmlFor="systemId" error={errors.systemId} required={false}>
        <Input
          id="systemId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.systemId ?? ''}
          onChange={(v) => set('systemId', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={OPTICAL_CHANNEL_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as OpticalChannelCriticality | undefined)}
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
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
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
