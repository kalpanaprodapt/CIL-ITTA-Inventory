import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { AMPLIFIER_AMP_TYPE_OPTIONS, AMPLIFIER_LIFECYCLE_STAGE_OPTIONS, AMPLIFIER_CRITICALITY_OPTIONS, AmplifierAmpType, AmplifierLifecycleStage, AmplifierCriticality } from './types';
import { validateAmplifier, cleanAmplifierValues } from './schema';
import { AMPLIFIER_DEFAULTS } from './types';
import type { AmplifierFormValues, AmplifierInput } from './types';

interface Props {
  initial?: AmplifierFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AmplifierInput) => void;
  onCancel?: () => void;
}

export function AmplifierForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AmplifierFormValues>({ ...AMPLIFIER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AmplifierFormValues>(key: K, value: AmplifierFormValues[K]) {
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
    const result = validateAmplifier(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAmplifierValues(values as Record<string, unknown>) as unknown as AmplifierInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Amplifier form">
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
      <FormField label="System Id" htmlFor="systemId" error={errors.systemId} required={true}>
        <Input
          id="systemId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.systemId ?? ''}
          onChange={(v) => set('systemId', v)}
        />
      </FormField>
      <FormField label="Amp Type" htmlFor="ampType" error={errors.ampType} required={false}>
        <Select
          id="ampType"
          value={values.ampType ?? ''}
          options={AMPLIFIER_AMP_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('ampType', (v || undefined) as AmplifierAmpType | undefined)}
        />
      </FormField>
      <FormField label="Gain Db" htmlFor="gainDb" error={errors.gainDb} required={true}>
        <NumberInput
          id="gainDb"
          value={values.gainDb}
          step={0.0001}
          onChange={(v) => set('gainDb', v)}
        />
      </FormField>
      <FormField label="Output Power Dbm" htmlFor="outputPowerDbm" error={errors.outputPowerDbm} required={false}>
        <NumberInput
          id="outputPowerDbm"
          value={values.outputPowerDbm}
          step={0.0001}
          onChange={(v) => set('outputPowerDbm', v)}
        />
      </FormField>
      <FormField label="Noise Figure Db" htmlFor="noiseFigureDb" error={errors.noiseFigureDb} required={false}>
        <NumberInput
          id="noiseFigureDb"
          value={values.noiseFigureDb}
          step={0.0001}
          onChange={(v) => set('noiseFigureDb', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={AMPLIFIER_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as AmplifierLifecycleStage | undefined)}
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
          options={AMPLIFIER_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as AmplifierCriticality | undefined)}
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
