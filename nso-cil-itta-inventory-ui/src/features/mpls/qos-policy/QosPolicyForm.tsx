import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { QOS_POLICY_LIFECYCLE_STAGE_OPTIONS, QosPolicyLifecycleStage } from './types';
import { validateQosPolicy, cleanQosPolicyValues } from './schema';
import { QOSPOLICY_DEFAULTS } from './types';
import type { QosPolicyFormValues, QosPolicyInput } from './types';

interface Props {
  initial?: QosPolicyFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: QosPolicyInput) => void;
  onCancel?: () => void;
}

export function QosPolicyForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<QosPolicyFormValues>({ ...QOSPOLICY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof QosPolicyFormValues>(key: K, value: QosPolicyFormValues[K]) {
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
    const result = validateQosPolicy(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanQosPolicyValues(values as Record<string, unknown>) as unknown as QosPolicyInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Qos Policy form">
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
      <FormField label="Policy Name" htmlFor="policyName" error={errors.policyName} required={true}>
        <Input
          id="policyName"
          value={values.policyName ?? ''}
          maxLength={255}
          onChange={(v) => set('policyName', v)}
        />
      </FormField>
      <FormField label="Class Count" htmlFor="classCount" error={errors.classCount} required={true}>
        <NumberInput
          id="classCount"
          value={values.classCount}
          step={1}
          onChange={(v) => set('classCount', v)}
        />
      </FormField>
      <FormField label="Cir Mbps" htmlFor="cirMbps" error={errors.cirMbps} required={false}>
        <NumberInput
          id="cirMbps"
          value={values.cirMbps}
          step={1}
          onChange={(v) => set('cirMbps', v)}
        />
      </FormField>
      <FormField label="Pir Mbps" htmlFor="pirMbps" error={errors.pirMbps} required={false}>
        <NumberInput
          id="pirMbps"
          value={values.pirMbps}
          step={1}
          onChange={(v) => set('pirMbps', v)}
        />
      </FormField>
      <FormField label="Marking Scheme" htmlFor="markingScheme" error={errors.markingScheme} required={false}>
        <Input
          id="markingScheme"
          value={values.markingScheme ?? ''}
          maxLength={255}
          onChange={(v) => set('markingScheme', v)}
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
          options={QOS_POLICY_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as QosPolicyLifecycleStage | undefined)}
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
